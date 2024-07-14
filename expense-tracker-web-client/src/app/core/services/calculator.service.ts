import { Injectable } from "@angular/core";

enum MathOperationType { Addition, Subtraction, Multiplication, Division }
type MathOperationProcessor = (left: number, right: number) => number;
type NullableString = string | null;

export class CalculationResult {
    private constructor(public success: boolean,
                        public value: number) { }

    static succeeded(value: number) : CalculationResult {
        return new CalculationResult(true, value);
    }

    public static failed(): CalculationResult {
        return new CalculationResult(false, -1);
    }
}

@Injectable({ providedIn: 'root' })
export class CalculatorService {
    private readonly PRECISION = 2;
    private readonly OPENING_BRACKET = '(';
    private readonly CLOSING_BRACKET = ')';
    private readonly DIGIT_EXPRESSION: RegExp = /^\d$/;
    private readonly operationSymbolMap = new Map<string, MathOperationType>([
        [ '+', MathOperationType.Addition ],
        [ '-', MathOperationType.Subtraction ],
        [ '*', MathOperationType.Multiplication ],
        [ '/', MathOperationType.Division ],
    ]);
    private operationProcessors = new Map<MathOperationType, MathOperationProcessor>([
        [  MathOperationType.Addition, (left, right) => left + right ],
        [  MathOperationType.Subtraction, (left, right) => left - right ],
        [  MathOperationType.Multiplication, (left, right) => left * right ],
        [  MathOperationType.Division, (left, right) => left / right ],
    ]);

    public calculate(expression: NullableString): CalculationResult {
        if (this.isExpressionNullOrWhiteSpace(expression)) {
            return CalculationResult.failed();
        }

        let regex = /[^\d\s*+\-/().]+/;
        if (regex.test(expression as string)) {
            return CalculationResult.failed();
        }

        if (expression) {
            expression = expression.trim();
        }

        if (this.isExpressionNumber(expression)) {
            let value = Number.parseFloat(expression as string);
            return Number.isNaN(value)
                ? CalculationResult.failed()
                : CalculationResult.succeeded(value);
        }

        let processed: NullableString = expression;
        processed = this.processBrackets(processed);
        processed = this.processOperations(processed, MathOperationType.Multiplication, MathOperationType.Division);
        processed = this.processOperations(processed, MathOperationType.Addition, MathOperationType.Subtraction);
        return this.isExpressionNumber(processed) && !Number.isNaN(Number.parseFloat(processed as string))
            ? CalculationResult.succeeded(Number.parseFloat(processed as string))
            : CalculationResult.failed();
    }

    private processBrackets(expression: NullableString): NullableString {
        if (this.isExpressionNullOrWhiteSpace(expression)) {
            return null;
        }

        let result: string = expression as string;
        while (this.hasExpressionBrackets(result)) {
            let closingBracketIndex: number = result.indexOf(this.CLOSING_BRACKET);
            if (closingBracketIndex === -1) {
                return null;
            }

            let openingBracketIndex: number = result
                .substring(0, closingBracketIndex)
                .lastIndexOf(this.OPENING_BRACKET);
            if (openingBracketIndex === -1) {
                return null;
            }

            let inBracketsExpression: string = result
                .substring(0, closingBracketIndex)
                .substring(openingBracketIndex + 1);
            let calculationResult: CalculationResult = this.calculate(inBracketsExpression);
            if (calculationResult.success === false) {
                return null;
            }

            let expressionToReplace = this.OPENING_BRACKET + inBracketsExpression + this.CLOSING_BRACKET;
            result = result.replace(expressionToReplace, calculationResult.value.toString());
        }
        return result;
    }

    private processOperations(expression: NullableString, ...selectedOperations: MathOperationType[]): NullableString {
        if (this.isExpressionNullOrWhiteSpace(expression)) {
            return null;
        }

        let processedExpression: string = expression as string;
        let allOperationSymbols: string[] = Array.from(this.operationSymbolMap.keys());
        let selectedOperationSymbols: string[] = Array.from(this.operationSymbolMap.entries())
            .filter(pair => selectedOperations.includes(pair[1]))
            .map(pair => pair[0]);

        while (this.indexOfAny(processedExpression, selectedOperationSymbols) !== -1) {
            let actionIndex: number = this.indexOfAny(processedExpression, selectedOperationSymbols);
            let leftOperandAsString: string = processedExpression.substring(0, actionIndex);
            let rightOperandAsString: string = processedExpression.substring(actionIndex + 1);

            if (this.isExpressionNullOrWhiteSpace(leftOperandAsString) || this.isExpressionNullOrWhiteSpace(rightOperandAsString)) {
                return null;
            }

            if (this.isExpressionNumber(leftOperandAsString) === false) {
                let operandStart = this.lastIndexOfAny(leftOperandAsString, allOperationSymbols) + 1;
                leftOperandAsString = leftOperandAsString.substring(operandStart);
            }

            if (!this.isExpressionNumber(rightOperandAsString)) {
                let operandEnd = this.indexOfAny(rightOperandAsString, allOperationSymbols);
                rightOperandAsString = rightOperandAsString.substring(0, operandEnd);
            }

            if (!this.isExpressionNumber(leftOperandAsString) || !this.isExpressionNumber(rightOperandAsString)) {
                return null;
            }

            let actionSymbol = processedExpression[actionIndex] as string;
            let actionType: MathOperationType = this.operationSymbolMap.get(actionSymbol) as MathOperationType;
            let processor: MathOperationProcessor = this.operationProcessors.get(actionType) as MathOperationProcessor;
            let calculated: number = processor(Number.parseFloat(leftOperandAsString.trim()), Number.parseFloat(rightOperandAsString.trim()));
            if (calculated % 1 !== 0) { calculated = Number.parseFloat(calculated.toPrecision(this.PRECISION)); }

            let expressionToReplace = leftOperandAsString + actionSymbol + rightOperandAsString;
            processedExpression = processedExpression.replace(expressionToReplace, calculated.toString());
        }

        return processedExpression;
    }

    private isExpressionNumber(expression: NullableString): boolean {
        if (this.isExpressionNullOrWhiteSpace(expression)) {
            return false;
        }
        let input = expression as string;
        if (input.startsWith('-')) {
            input = input.substring(1);
        }
        for (let i = 0; i < input.length; ++i) {
            if (this.isDigit(input.charAt(i)) === false && input.charAt(i) !== '.') {
                return false;
            }
        }
        return true;
    }

    private isExpressionNullOrWhiteSpace(expression: NullableString): boolean {
        return expression === null || expression == undefined || expression === '';
    }

    private hasExpressionBrackets(expression: NullableString): boolean {
        if (expression === null) { return false; }
        return expression.indexOf(this.OPENING_BRACKET) !== -1
            || expression.indexOf(this.CLOSING_BRACKET) !== -1;
    }

    private indexOfAny(input: string, strings: string[]) {
        let result: number | null = null;
        for(const s of strings) {
            let indexOfStr = input.indexOf(s);
            if ((indexOfStr !== -1) && (result == null || result > indexOfStr)) {
                result = indexOfStr;
            }
        }
        return result ?? -1;
    }

    private lastIndexOfAny(input: string, strings: string[]) {
        for(let i = strings.length - 1; i >= 0; --i) {
            let str = strings[i] as string;
            let indexOfStr = input.indexOf(str);
            if (indexOfStr !== -1) {
                return indexOfStr;
            }
        }
        return -1;
    }

    private isDigit(character: string): boolean {
        return !!character && this.DIGIT_EXPRESSION.test(character);
    }
}

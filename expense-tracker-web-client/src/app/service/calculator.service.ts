import { CalculationResult } from "../model/calculation-result";

enum MathOperationType { Addition, Subtraction, Multiplication, Division }
type MathOperationProcessor = (left: number, right: number) => number;

export class CalculatorService {
    constructor(private precision: number) {
    }

    private readonly OPENING_BRACKET = '(';
    private readonly CLOSING_BRACKET = ')';
    private readonly operationSymbolMap: Map<string, MathOperationType> = new Map<string, MathOperationType>([
        [ '+', MathOperationType.Addition ],
        [ '-', MathOperationType.Subtraction ],
        [ '*', MathOperationType.Multiplication ],
        [ '/', MathOperationType.Division ],
    ]);
    private operationProcessors: Map<MathOperationType, MathOperationProcessor> = new Map<MathOperationType, MathOperationProcessor>([
        [  MathOperationType.Addition, (left, right) => left + right ],
        [  MathOperationType.Subtraction, (left, right) => left - right ],
        [  MathOperationType.Multiplication, (left, right) => left * right ],
        [  MathOperationType.Division, (left, right) => left / right ],
    ]);

    public calculate(expression: string): CalculationResult {
        if (this.isExpressionNullOrWhiteSpace(expression)) {
            return CalculationResult.failed();
        }

        if (this.isExpressionNumber(expression)) {
            let value = Number.parseFloat(expression);
            return CalculationResult.succeeded(value);
        }

        let processed: string = expression;
        processed = this.processBrackets(processed);
        processed = this.processOperations(processed, MathOperationType.Multiplication, MathOperationType.Division);
        processed = this.processOperations(processed, MathOperationType.Addition, MathOperationType.Subtraction);
        return this.isExpressionNumber(processed) ? CalculationResult.succeeded(Number.parseFloat(processed)) : CalculationResult.failed();
    }

    private processBrackets(expression: string): string {
        if (this.isExpressionNullOrWhiteSpace(expression)) {
            return null;
        }

        let result: string = expression;
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

    private processOperations(expression: string, ...selectedOperations: MathOperationType[]): string {
        if (this.isExpressionNullOrWhiteSpace(expression)) {
            return null;
        }

        let processedExpression: string = expression;
        let allOperationSymbols: string[] = Array.from(this.operationSymbolMap.keys());
        let selectedOperationSymbols: string[] = Array.from(this.operationSymbolMap.entries())
            .filter(pair => selectedOperations.includes(pair[1] as MathOperationType))
            .map(pair => pair[0] as string);

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

            let actionSymbol = processedExpression[actionIndex];
            let actionType: MathOperationType = this.operationSymbolMap.get(actionSymbol);
            let processor: MathOperationProcessor = this.operationProcessors.get(actionType);
            let calculated: number = processor(Number.parseFloat(leftOperandAsString.trim()), Number.parseFloat(rightOperandAsString.trim()));
            calculated = Number.parseFloat(calculated.toPrecision(this.precision));

            let expressionToReplace = leftOperandAsString + actionSymbol + rightOperandAsString;
            processedExpression = processedExpression.replace(expressionToReplace, calculated.toString());
        }

        return processedExpression;
    }

    private isExpressionNumber(expression: string): boolean {
        return this.isExpressionNullOrWhiteSpace(expression) === false
                && this.indexOfAny(expression, Array.from(this.operationSymbolMap.keys())) === -1
                && this.indexOfAny(expression, [ this.OPENING_BRACKET, this.CLOSING_BRACKET ]) === -1;
    }

    private isExpressionNullOrWhiteSpace(expression: string): boolean {
        return expression === null || expression == undefined || expression === '';
    }

    private hasExpressionBrackets(expression: string): boolean {
        return expression.indexOf(this.OPENING_BRACKET) !== -1
            || expression.indexOf(this.CLOSING_BRACKET) !== -1;
    }

    private indexOfAny(input: string, strings: string[]) {
        for(let i = 0; i < strings.length; ++i) {
            let str = strings[i];
            let indexOfStr = input.indexOf(str);
            if (indexOfStr !== -1) {
                return indexOfStr;
            }
        }
        return -1;
    }

    private lastIndexOfAny(input: string, strings: string[]) {
        for(let i = strings.length - 1; i >= 0; --i) {
            let str = strings[i];
            let indexOfStr = input.indexOf(str);
            if (indexOfStr !== -1) {
                return indexOfStr;
            }
        }
        return -1;
    }
}

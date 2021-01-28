
export class CalculationResult
{
    private constructor(
        public success: boolean,
        public value: number
    ) { }

    static succeeded(value: number) : CalculationResult {
        return new CalculationResult(true, value);
    }

    public static failed(): CalculationResult {
        return new CalculationResult(false, -1);
    }
}

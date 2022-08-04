import {} from 'jasmine';
import { CalculatorService } from "@core/services";

describe('Calculator', () => {
    let service = new CalculatorService();

    it('Number', (done: DoneFn) => {
        assertSucceeded('1', 1);
        assertSucceeded('10', 10);
        done();
    });

    it('Float', (done: DoneFn) => {
        assertSucceeded('1.20', 1.2);
        assertSucceeded('10.48', 10.48);
        done();
    });

    it('Addition', (done: DoneFn) => {
        assertSucceeded('1 + 2', 3);
        assertSucceeded('123 + 123', 246);
        assertSucceeded('0.1 + 0.2', 0.3);
        done();
    });

    it('Substraction', (done: DoneFn) => {
        assertSucceeded('10 - 6', 4);
        assertSucceeded('0.3 - 0.2', 0.1);
        done();
    });

    it('Miltiplication', (done: DoneFn) => {
        assertSucceeded('10 * 6', 60);
        assertSucceeded('0.2 * 0.3', 0.06);
        done();
    });

    it('Division', (done: DoneFn) => {
        assertSucceeded('10 / 5', 2);
        assertSucceeded('0.3 / 0.2', 1.5);
        done();
    });

    it('Single Brackets', (done: DoneFn) => {
        assertSucceeded('2 * (3 + 5)', 16);
        assertSucceeded('6 * (4 - 2)', 12);
        done();
    });

    it('Multiple Brackets', (done: DoneFn) => {
        assertSucceeded('2 * (3 + 5) * (4 - 2)', 32);
        assertSucceeded('6 * (4 - 2) / (12 - 10)', 6);
        done();
    });

    it('Nested Brackets', (done: DoneFn) => {
        assertSucceeded('6 * (4 + 2 * (3 - 2))', 36);
        assertSucceeded('6 * (4 - 2) / (12 / (6 - 2))', 4);
        done();
    });

    it ('Invalid Expression', (done: DoneFn) => {
        assertFailed('TEST');
        assertFailed('1200 + S850');
        done();
    })

    function assertSucceeded(expression: string, exprectedResult: number): void {
        let actualResult = service.calculate(expression);
        expect(actualResult).toBeDefined();
        expect(actualResult.success).toBeTrue();
        expect(actualResult.value).toBe(exprectedResult);
    }

    function assertFailed(expression: string): void {
        let actualResult = service.calculate(expression);
        expect(actualResult).toBeDefined();
        expect(actualResult.success).toBeFalse();
        expect(actualResult.value).toBe(-1);
    }
});

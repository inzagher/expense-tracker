
export class MathUtils {
    static sum<T>(items: T[], by: (i: T) => number): number {
        return items.map(item => by(item)).reduce(MathUtils.add, 0);
    }

    private static add(sum: number | null, next: number | null): number {
        return (sum ?? 0) + (next ?? 0);
    }
}

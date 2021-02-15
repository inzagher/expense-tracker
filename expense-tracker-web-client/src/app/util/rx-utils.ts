import { Observable } from "rxjs";

export class RxUtils {
    public static asObservable<T>(action: () => T): Observable<T> {
        return new Observable<T>((observer) =>  {
            try { observer.next(action()); observer.complete(); }
            catch(error: any) { observer.error(error); }
        });
    }
}

import { Pageable } from "./pageable";
import { Sort } from "./sort";

export interface Page<T> {
        content: Array<T>,
        pageable:Pageable,
        totalPages: number,
        totalElements: number,
        last: boolean,
        numberOfElements: number,
        sort: Sort,
        first: boolean,
        size: number,
        number: number,
        empty: boolean
}

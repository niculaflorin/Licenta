import { BaseEntity } from './../../shared';

export class DocumentSentiment implements BaseEntity {
    constructor(
        public id?: number,
        public magnitude?: number,
        public score?: number,
        public language?: string,
        public sentences?: BaseEntity[],
    ) {
    }
}

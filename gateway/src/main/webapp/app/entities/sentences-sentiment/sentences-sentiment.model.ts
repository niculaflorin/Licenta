import { BaseEntity } from './../../shared';

export class SentencesSentiment implements BaseEntity {
    constructor(
        public id?: number,
        public content?: string,
        public magnitude?: number,
        public score?: number,
        public documentSentiment?: BaseEntity,
    ) {
    }
}

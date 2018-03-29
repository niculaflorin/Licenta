import { BaseEntity } from './../../shared';

export class InitialText implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public userId?: number,
        public initial?: BaseEntity,
        public subject?: BaseEntity,
    ) {
    }
}

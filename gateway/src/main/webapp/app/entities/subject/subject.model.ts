import { BaseEntity } from './../../shared';

export class Subject implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public texts?: BaseEntity[],
    ) {
    }
}

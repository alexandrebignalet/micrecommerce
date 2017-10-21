import { BaseEntity } from './../../shared';

export class Image implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public fileContentType?: string,
        public file?: any,
    ) {
    }
}

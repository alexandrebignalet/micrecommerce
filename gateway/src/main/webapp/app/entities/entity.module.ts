import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayProductModule } from './product/product.module';
import { GatewayImageModule } from './image/image.module';
import { GatewayCategoryModule } from './category/category.module';
import { GatewayTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GatewayProductModule,
        GatewayImageModule,
        GatewayCategoryModule,
        GatewayTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}

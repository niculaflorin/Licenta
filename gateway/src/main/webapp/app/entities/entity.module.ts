import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayInitialTextModule } from './initial-text/initial-text.module';
import { GatewayDocumentSentimentModule } from './document-sentiment/document-sentiment.module';
import { GatewaySubjectModule } from './subject/subject.module';
import { GatewaySentencesSentimentModule } from './sentences-sentiment/sentences-sentiment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GatewayInitialTextModule,
        GatewayDocumentSentimentModule,
        GatewaySubjectModule,
        GatewaySentencesSentimentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}

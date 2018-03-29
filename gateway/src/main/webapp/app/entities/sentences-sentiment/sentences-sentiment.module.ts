import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    SentencesSentimentService,
    SentencesSentimentPopupService,
    SentencesSentimentComponent,
    SentencesSentimentDetailComponent,
    SentencesSentimentDialogComponent,
    SentencesSentimentPopupComponent,
    SentencesSentimentDeletePopupComponent,
    SentencesSentimentDeleteDialogComponent,
    sentencesSentimentRoute,
    sentencesSentimentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sentencesSentimentRoute,
    ...sentencesSentimentPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SentencesSentimentComponent,
        SentencesSentimentDetailComponent,
        SentencesSentimentDialogComponent,
        SentencesSentimentDeleteDialogComponent,
        SentencesSentimentPopupComponent,
        SentencesSentimentDeletePopupComponent,
    ],
    entryComponents: [
        SentencesSentimentComponent,
        SentencesSentimentDialogComponent,
        SentencesSentimentPopupComponent,
        SentencesSentimentDeleteDialogComponent,
        SentencesSentimentDeletePopupComponent,
    ],
    providers: [
        SentencesSentimentService,
        SentencesSentimentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewaySentencesSentimentModule {}

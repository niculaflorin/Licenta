import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    DocumentSentimentService,
    DocumentSentimentPopupService,
    DocumentSentimentComponent,
    DocumentSentimentDetailComponent,
    DocumentSentimentDialogComponent,
    DocumentSentimentPopupComponent,
    DocumentSentimentDeletePopupComponent,
    DocumentSentimentDeleteDialogComponent,
    documentSentimentRoute,
    documentSentimentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...documentSentimentRoute,
    ...documentSentimentPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DocumentSentimentComponent,
        DocumentSentimentDetailComponent,
        DocumentSentimentDialogComponent,
        DocumentSentimentDeleteDialogComponent,
        DocumentSentimentPopupComponent,
        DocumentSentimentDeletePopupComponent,
    ],
    entryComponents: [
        DocumentSentimentComponent,
        DocumentSentimentDialogComponent,
        DocumentSentimentPopupComponent,
        DocumentSentimentDeleteDialogComponent,
        DocumentSentimentDeletePopupComponent,
    ],
    providers: [
        DocumentSentimentService,
        DocumentSentimentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayDocumentSentimentModule {}

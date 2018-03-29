import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    InitialTextService,
    InitialTextPopupService,
    InitialTextComponent,
    InitialTextDetailComponent,
    InitialTextDialogComponent,
    InitialTextPopupComponent,
    InitialTextDeletePopupComponent,
    InitialTextDeleteDialogComponent,
    initialTextRoute,
    initialTextPopupRoute,
} from './';

const ENTITY_STATES = [
    ...initialTextRoute,
    ...initialTextPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InitialTextComponent,
        InitialTextDetailComponent,
        InitialTextDialogComponent,
        InitialTextDeleteDialogComponent,
        InitialTextPopupComponent,
        InitialTextDeletePopupComponent,
    ],
    entryComponents: [
        InitialTextComponent,
        InitialTextDialogComponent,
        InitialTextPopupComponent,
        InitialTextDeleteDialogComponent,
        InitialTextDeletePopupComponent,
    ],
    providers: [
        InitialTextService,
        InitialTextPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayInitialTextModule {}

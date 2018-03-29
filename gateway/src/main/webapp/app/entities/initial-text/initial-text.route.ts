import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InitialTextComponent } from './initial-text.component';
import { InitialTextDetailComponent } from './initial-text-detail.component';
import { InitialTextPopupComponent } from './initial-text-dialog.component';
import { InitialTextDeletePopupComponent } from './initial-text-delete-dialog.component';

export const initialTextRoute: Routes = [
    {
        path: 'initial-text',
        component: InitialTextComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InitialTexts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'initial-text/:id',
        component: InitialTextDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InitialTexts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const initialTextPopupRoute: Routes = [
    {
        path: 'initial-text-new',
        component: InitialTextPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InitialTexts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'initial-text/:id/edit',
        component: InitialTextPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InitialTexts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'initial-text/:id/delete',
        component: InitialTextDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InitialTexts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

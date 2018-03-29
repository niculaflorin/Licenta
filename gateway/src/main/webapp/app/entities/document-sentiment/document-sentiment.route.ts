import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DocumentSentimentComponent } from './document-sentiment.component';
import { DocumentSentimentDetailComponent } from './document-sentiment-detail.component';
import { DocumentSentimentPopupComponent } from './document-sentiment-dialog.component';
import { DocumentSentimentDeletePopupComponent } from './document-sentiment-delete-dialog.component';

export const documentSentimentRoute: Routes = [
    {
        path: 'document-sentiment',
        component: DocumentSentimentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentSentiments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'document-sentiment/:id',
        component: DocumentSentimentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentSentiments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentSentimentPopupRoute: Routes = [
    {
        path: 'document-sentiment-new',
        component: DocumentSentimentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentSentiments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-sentiment/:id/edit',
        component: DocumentSentimentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentSentiments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-sentiment/:id/delete',
        component: DocumentSentimentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentSentiments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

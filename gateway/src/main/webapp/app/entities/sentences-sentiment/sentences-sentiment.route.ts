import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SentencesSentimentComponent } from './sentences-sentiment.component';
import { SentencesSentimentDetailComponent } from './sentences-sentiment-detail.component';
import { SentencesSentimentPopupComponent } from './sentences-sentiment-dialog.component';
import { SentencesSentimentDeletePopupComponent } from './sentences-sentiment-delete-dialog.component';

export const sentencesSentimentRoute: Routes = [
    {
        path: 'sentences-sentiment',
        component: SentencesSentimentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SentencesSentiments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sentences-sentiment/:id',
        component: SentencesSentimentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SentencesSentiments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sentencesSentimentPopupRoute: Routes = [
    {
        path: 'sentences-sentiment-new',
        component: SentencesSentimentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SentencesSentiments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sentences-sentiment/:id/edit',
        component: SentencesSentimentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SentencesSentiments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sentences-sentiment/:id/delete',
        component: SentencesSentimentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SentencesSentiments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

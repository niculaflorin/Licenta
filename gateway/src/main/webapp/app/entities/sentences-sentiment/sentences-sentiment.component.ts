import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SentencesSentiment } from './sentences-sentiment.model';
import { SentencesSentimentService } from './sentences-sentiment.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sentences-sentiment',
    templateUrl: './sentences-sentiment.component.html'
})
export class SentencesSentimentComponent implements OnInit, OnDestroy {
sentencesSentiments: SentencesSentiment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private sentencesSentimentService: SentencesSentimentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.sentencesSentimentService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.sentencesSentiments = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.sentencesSentimentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sentencesSentiments = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSentencesSentiments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SentencesSentiment) {
        return item.id;
    }
    registerChangeInSentencesSentiments() {
        this.eventSubscriber = this.eventManager.subscribe('sentencesSentimentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

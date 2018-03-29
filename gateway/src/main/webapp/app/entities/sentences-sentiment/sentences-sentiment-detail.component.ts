import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SentencesSentiment } from './sentences-sentiment.model';
import { SentencesSentimentService } from './sentences-sentiment.service';

@Component({
    selector: 'jhi-sentences-sentiment-detail',
    templateUrl: './sentences-sentiment-detail.component.html'
})
export class SentencesSentimentDetailComponent implements OnInit, OnDestroy {

    sentencesSentiment: SentencesSentiment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sentencesSentimentService: SentencesSentimentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSentencesSentiments();
    }

    load(id) {
        this.sentencesSentimentService.find(id).subscribe((sentencesSentiment) => {
            this.sentencesSentiment = sentencesSentiment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSentencesSentiments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sentencesSentimentListModification',
            (response) => this.load(this.sentencesSentiment.id)
        );
    }
}

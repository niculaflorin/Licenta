import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DocumentSentiment } from './document-sentiment.model';
import { DocumentSentimentService } from './document-sentiment.service';

@Component({
    selector: 'jhi-document-sentiment-detail',
    templateUrl: './document-sentiment-detail.component.html'
})
export class DocumentSentimentDetailComponent implements OnInit, OnDestroy {

    documentSentiment: DocumentSentiment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private documentSentimentService: DocumentSentimentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocumentSentiments();
    }

    load(id) {
        this.documentSentimentService.find(id).subscribe((documentSentiment) => {
            this.documentSentiment = documentSentiment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocumentSentiments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'documentSentimentListModification',
            (response) => this.load(this.documentSentiment.id)
        );
    }
}

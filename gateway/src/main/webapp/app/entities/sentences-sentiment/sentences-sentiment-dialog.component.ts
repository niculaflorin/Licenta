import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SentencesSentiment } from './sentences-sentiment.model';
import { SentencesSentimentPopupService } from './sentences-sentiment-popup.service';
import { SentencesSentimentService } from './sentences-sentiment.service';
import { DocumentSentiment, DocumentSentimentService } from '../document-sentiment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sentences-sentiment-dialog',
    templateUrl: './sentences-sentiment-dialog.component.html'
})
export class SentencesSentimentDialogComponent implements OnInit {

    sentencesSentiment: SentencesSentiment;
    isSaving: boolean;

    documentsentiments: DocumentSentiment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sentencesSentimentService: SentencesSentimentService,
        private documentSentimentService: DocumentSentimentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.documentSentimentService.query()
            .subscribe((res: ResponseWrapper) => { this.documentsentiments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sentencesSentiment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sentencesSentimentService.update(this.sentencesSentiment));
        } else {
            this.subscribeToSaveResponse(
                this.sentencesSentimentService.create(this.sentencesSentiment));
        }
    }

    private subscribeToSaveResponse(result: Observable<SentencesSentiment>) {
        result.subscribe((res: SentencesSentiment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SentencesSentiment) {
        this.eventManager.broadcast({ name: 'sentencesSentimentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDocumentSentimentById(index: number, item: DocumentSentiment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sentences-sentiment-popup',
    template: ''
})
export class SentencesSentimentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sentencesSentimentPopupService: SentencesSentimentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sentencesSentimentPopupService
                    .open(SentencesSentimentDialogComponent as Component, params['id']);
            } else {
                this.sentencesSentimentPopupService
                    .open(SentencesSentimentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

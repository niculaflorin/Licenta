import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DocumentSentiment } from './document-sentiment.model';
import { DocumentSentimentPopupService } from './document-sentiment-popup.service';
import { DocumentSentimentService } from './document-sentiment.service';

@Component({
    selector: 'jhi-document-sentiment-dialog',
    templateUrl: './document-sentiment-dialog.component.html'
})
export class DocumentSentimentDialogComponent implements OnInit {

    documentSentiment: DocumentSentiment;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private documentSentimentService: DocumentSentimentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.documentSentiment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.documentSentimentService.update(this.documentSentiment));
        } else {
            this.subscribeToSaveResponse(
                this.documentSentimentService.create(this.documentSentiment));
        }
    }

    private subscribeToSaveResponse(result: Observable<DocumentSentiment>) {
        result.subscribe((res: DocumentSentiment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DocumentSentiment) {
        this.eventManager.broadcast({ name: 'documentSentimentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-document-sentiment-popup',
    template: ''
})
export class DocumentSentimentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentSentimentPopupService: DocumentSentimentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.documentSentimentPopupService
                    .open(DocumentSentimentDialogComponent as Component, params['id']);
            } else {
                this.documentSentimentPopupService
                    .open(DocumentSentimentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DocumentSentiment } from './document-sentiment.model';
import { DocumentSentimentPopupService } from './document-sentiment-popup.service';
import { DocumentSentimentService } from './document-sentiment.service';

@Component({
    selector: 'jhi-document-sentiment-delete-dialog',
    templateUrl: './document-sentiment-delete-dialog.component.html'
})
export class DocumentSentimentDeleteDialogComponent {

    documentSentiment: DocumentSentiment;

    constructor(
        private documentSentimentService: DocumentSentimentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentSentimentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'documentSentimentListModification',
                content: 'Deleted an documentSentiment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-sentiment-delete-popup',
    template: ''
})
export class DocumentSentimentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentSentimentPopupService: DocumentSentimentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.documentSentimentPopupService
                .open(DocumentSentimentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SentencesSentiment } from './sentences-sentiment.model';
import { SentencesSentimentPopupService } from './sentences-sentiment-popup.service';
import { SentencesSentimentService } from './sentences-sentiment.service';

@Component({
    selector: 'jhi-sentences-sentiment-delete-dialog',
    templateUrl: './sentences-sentiment-delete-dialog.component.html'
})
export class SentencesSentimentDeleteDialogComponent {

    sentencesSentiment: SentencesSentiment;

    constructor(
        private sentencesSentimentService: SentencesSentimentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sentencesSentimentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sentencesSentimentListModification',
                content: 'Deleted an sentencesSentiment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sentences-sentiment-delete-popup',
    template: ''
})
export class SentencesSentimentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sentencesSentimentPopupService: SentencesSentimentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sentencesSentimentPopupService
                .open(SentencesSentimentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

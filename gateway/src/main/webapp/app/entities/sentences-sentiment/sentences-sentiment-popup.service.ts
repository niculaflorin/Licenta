import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SentencesSentiment } from './sentences-sentiment.model';
import { SentencesSentimentService } from './sentences-sentiment.service';

@Injectable()
export class SentencesSentimentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private sentencesSentimentService: SentencesSentimentService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.sentencesSentimentService.find(id).subscribe((sentencesSentiment) => {
                    this.ngbModalRef = this.sentencesSentimentModalRef(component, sentencesSentiment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sentencesSentimentModalRef(component, new SentencesSentiment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sentencesSentimentModalRef(component: Component, sentencesSentiment: SentencesSentiment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sentencesSentiment = sentencesSentiment;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InitialText } from './initial-text.model';
import { InitialTextPopupService } from './initial-text-popup.service';
import { InitialTextService } from './initial-text.service';
import { DocumentSentiment, DocumentSentimentService } from '../document-sentiment';
import { Subject, SubjectService } from '../subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-initial-text-dialog',
    templateUrl: './initial-text-dialog.component.html'
})
export class InitialTextDialogComponent implements OnInit {

    initialText: InitialText;
    isSaving: boolean;
    userLogged: number;

    initials: DocumentSentiment[];

    subjects: Subject[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private initialTextService: InitialTextService,
        private documentSentimentService: DocumentSentimentService,
        private subjectService: SubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.documentSentimentService
            .query({filter: 'initialtext-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.initialText.initial || !this.initialText.initial.id) {
                    this.initials = res.json;
                } else {
                    this.documentSentimentService
                        .find(this.initialText.initial.id)
                        .subscribe((subRes: DocumentSentiment) => {
                            this.initials = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService.query()
            .subscribe((res: ResponseWrapper) => { this.subjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.initialText.userId = this.userLogged;
        if (this.initialText.id !== undefined) {
            this.subscribeToSaveResponse(
                this.initialTextService.update(this.initialText));
        } else {
            this.subscribeToSaveResponse(
                this.initialTextService.create(this.initialText));
        }
    }

    private subscribeToSaveResponse(result: Observable<InitialText>) {
        result.subscribe((res: InitialText) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InitialText) {
        this.eventManager.broadcast({ name: 'initialTextListModification', content: 'OK'});
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

    trackSubjectById(index: number, item: Subject) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-initial-text-popup',
    template: ''
})
export class InitialTextPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private initialTextPopupService: InitialTextPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.initialTextPopupService
                    .open(InitialTextDialogComponent as Component, params['id']);
            } else {
                this.initialTextPopupService
                    .open(InitialTextDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

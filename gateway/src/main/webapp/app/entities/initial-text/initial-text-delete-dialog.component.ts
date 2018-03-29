import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InitialText } from './initial-text.model';
import { InitialTextPopupService } from './initial-text-popup.service';
import { InitialTextService } from './initial-text.service';

@Component({
    selector: 'jhi-initial-text-delete-dialog',
    templateUrl: './initial-text-delete-dialog.component.html'
})
export class InitialTextDeleteDialogComponent {

    initialText: InitialText;

    constructor(
        private initialTextService: InitialTextService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.initialTextService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'initialTextListModification',
                content: 'Deleted an initialText'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-initial-text-delete-popup',
    template: ''
})
export class InitialTextDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private initialTextPopupService: InitialTextPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.initialTextPopupService
                .open(InitialTextDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

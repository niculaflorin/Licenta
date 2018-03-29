import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InitialText } from './initial-text.model';
import { InitialTextService } from './initial-text.service';

@Component({
    selector: 'jhi-initial-text-detail',
    templateUrl: './initial-text-detail.component.html'
})
export class InitialTextDetailComponent implements OnInit, OnDestroy {

    initialText: InitialText;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private initialTextService: InitialTextService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInitialTexts();
    }

    load(id) {
        this.initialTextService.find(id).subscribe((initialText) => {
            this.initialText = initialText;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInitialTexts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'initialTextListModification',
            (response) => this.load(this.initialText.id)
        );
    }
}

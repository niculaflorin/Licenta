/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SentencesSentimentDetailComponent } from '../../../../../../main/webapp/app/entities/sentences-sentiment/sentences-sentiment-detail.component';
import { SentencesSentimentService } from '../../../../../../main/webapp/app/entities/sentences-sentiment/sentences-sentiment.service';
import { SentencesSentiment } from '../../../../../../main/webapp/app/entities/sentences-sentiment/sentences-sentiment.model';

describe('Component Tests', () => {

    describe('SentencesSentiment Management Detail Component', () => {
        let comp: SentencesSentimentDetailComponent;
        let fixture: ComponentFixture<SentencesSentimentDetailComponent>;
        let service: SentencesSentimentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SentencesSentimentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SentencesSentimentService,
                    JhiEventManager
                ]
            }).overrideTemplate(SentencesSentimentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SentencesSentimentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SentencesSentimentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SentencesSentiment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sentencesSentiment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

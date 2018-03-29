/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DocumentSentimentDetailComponent } from '../../../../../../main/webapp/app/entities/document-sentiment/document-sentiment-detail.component';
import { DocumentSentimentService } from '../../../../../../main/webapp/app/entities/document-sentiment/document-sentiment.service';
import { DocumentSentiment } from '../../../../../../main/webapp/app/entities/document-sentiment/document-sentiment.model';

describe('Component Tests', () => {

    describe('DocumentSentiment Management Detail Component', () => {
        let comp: DocumentSentimentDetailComponent;
        let fixture: ComponentFixture<DocumentSentimentDetailComponent>;
        let service: DocumentSentimentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DocumentSentimentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DocumentSentimentService,
                    JhiEventManager
                ]
            }).overrideTemplate(DocumentSentimentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentSentimentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentSentimentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DocumentSentiment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.documentSentiment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

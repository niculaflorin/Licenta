/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InitialTextDetailComponent } from '../../../../../../main/webapp/app/entities/initial-text/initial-text-detail.component';
import { InitialTextService } from '../../../../../../main/webapp/app/entities/initial-text/initial-text.service';
import { InitialText } from '../../../../../../main/webapp/app/entities/initial-text/initial-text.model';

describe('Component Tests', () => {

    describe('InitialText Management Detail Component', () => {
        let comp: InitialTextDetailComponent;
        let fixture: ComponentFixture<InitialTextDetailComponent>;
        let service: InitialTextService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [InitialTextDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InitialTextService,
                    JhiEventManager
                ]
            }).overrideTemplate(InitialTextDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InitialTextDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InitialTextService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InitialText(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.initialText).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

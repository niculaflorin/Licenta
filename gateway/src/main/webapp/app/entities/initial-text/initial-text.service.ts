import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { InitialText } from './initial-text.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InitialTextService {

    private resourceUrl = '/googlecognitive/api/initial-texts';
    private resourceSearchUrl = '/googlecognitive/api/_search/initial-texts';

    constructor(private http: Http) { }

    create(initialText: InitialText): Observable<InitialText> {
        const copy = this.convert(initialText);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(initialText: InitialText): Observable<InitialText> {
        const copy = this.convert(initialText);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<InitialText> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to InitialText.
     */
    private convertItemFromServer(json: any): InitialText {
        const entity: InitialText = Object.assign(new InitialText(), json);
        return entity;
    }

    /**
     * Convert a InitialText to a JSON which can be sent to the server.
     */
    private convert(initialText: InitialText): InitialText {
        const copy: InitialText = Object.assign({}, initialText);
        return copy;
    }
}

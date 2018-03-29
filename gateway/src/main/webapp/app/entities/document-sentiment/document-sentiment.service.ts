import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DocumentSentiment } from './document-sentiment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DocumentSentimentService {

    private resourceUrl = '/googlecognitive/api/document-sentiments';
    private resourceSearchUrl = '/googlecognitive/api/_search/document-sentiments';

    constructor(private http: Http) { }

    create(documentSentiment: DocumentSentiment): Observable<DocumentSentiment> {
        const copy = this.convert(documentSentiment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(documentSentiment: DocumentSentiment): Observable<DocumentSentiment> {
        const copy = this.convert(documentSentiment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DocumentSentiment> {
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
     * Convert a returned JSON object to DocumentSentiment.
     */
    private convertItemFromServer(json: any): DocumentSentiment {
        const entity: DocumentSentiment = Object.assign(new DocumentSentiment(), json);
        return entity;
    }

    /**
     * Convert a DocumentSentiment to a JSON which can be sent to the server.
     */
    private convert(documentSentiment: DocumentSentiment): DocumentSentiment {
        const copy: DocumentSentiment = Object.assign({}, documentSentiment);
        return copy;
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MergeDTO } from '../entities/MergeDTO';

@Injectable({
    providedIn: 'root',
})
export class MergeService {
    private apiUrl = 'http://localhost:8080/api/merges';

    constructor(private http: HttpClient) { }

    // Create a new merge
    createMerge(mergeDTO: MergeDTO, classroomId: number): Observable<MergeDTO> {
        return this.http.post<MergeDTO>(`${this.apiUrl}/classroom/${classroomId}`, mergeDTO);
    }

    // Get all merges
    getAllMerges(): Observable<MergeDTO[]> {
        return this.http.get<MergeDTO[]>(this.apiUrl);
    }

    // Get merges by classroom ID
    getMergesByClassroomId(classroomId: number): Observable<MergeDTO[]> {
        return this.http.get<MergeDTO[]>(`${this.apiUrl}/classroom/${classroomId}`);
    }

    // Get merge by ID
    getMergeById(mergeId: number): Observable<MergeDTO> {
        return this.http.get<MergeDTO>(`${this.apiUrl}/${mergeId}`);
    }

    // Delete a merge by ID
    deleteMerge(mergeId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${mergeId}`);
    }
}

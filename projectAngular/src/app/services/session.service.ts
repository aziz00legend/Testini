// src/app/services/session.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SessionDTO } from '../entities/Session';
import { SessionNodeDto } from '../entities/SessionNodeDto';
import { SessionSaveUpdateDTO } from '../entities/sessionSaveUpdateDTO';


@Injectable({
    providedIn: 'root'
})
export class SessionService {

    private apiUrl = `http://localhost:8080/api/sessions`;

    constructor(private http: HttpClient) { }

    // Get all sessions
    getAllSessions(): Observable<SessionDTO[]> {
        console.log(this.http.get<SessionDTO[]>(`${this.apiUrl}/all`))
        return this.http.get<SessionDTO[]>(`${this.apiUrl}/all`);
    }

    // Get a session by ID
    getSessionById(id: number): Observable<SessionDTO> {
        return this.http.get<SessionDTO>(`${this.apiUrl}/${id}`);
    }

    // Create a new session
    createSession(session: SessionSaveUpdateDTO, challengeId: number, classroomId: number): Observable<void> {
        const params = { challengeId: challengeId.toString(), classroomId: classroomId.toString() };
        return this.http.post<void>(this.apiUrl, session, { params });
    }

    // Delete a session by ID
    deleteSession(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    // Get static integer (for testing purposes)
    getStaticInteger(): Observable<number> {
        return this.http.get<number>(`${this.apiUrl}/int`);
    }



    // Get the treenization of sessions by classroom ID
    treenizationSessionByClassroomId(classroomId: number): Observable<SessionNodeDto[]> {
        return this.http.get<SessionNodeDto[]>(`${this.apiUrl}/treenization/${classroomId}`);
    }
}

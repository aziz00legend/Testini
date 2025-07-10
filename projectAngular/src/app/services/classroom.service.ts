import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClassroomDTO } from '../entities/ClassroomDTO';
import { ClassroomUpdateSaveDTO } from '../entities/ClassroomUpdateSaveDTO';

@Injectable({
    providedIn: 'root',
})
export class ClassroomService {
    private apiUrl = 'http://localhost:8080/api/classrooms';

    constructor(private http: HttpClient) { }


    saveClassroom(classroom: ClassroomUpdateSaveDTO, instructorId: number): Observable<string> {
        const params = new HttpParams().set('instructorId', instructorId.toString());
        return this.http.post<string>(this.apiUrl, classroom, { params, responseType: 'text' as 'json' });
    }



    updateClassroom(id: number, classroom: ClassroomUpdateSaveDTO): Observable<string> {
        return this.http.put<string>(`${this.apiUrl}/${id}`, classroom, { responseType: 'text' as 'json' });
    }



    getAllClassrooms(): Observable<ClassroomDTO[]> {
        return this.http.get<ClassroomDTO[]>(this.apiUrl);
    }

    getAllClassroomsByInstructorId(instructorId: number): Observable<ClassroomDTO[]> {
        return this.http.get<ClassroomDTO[]>(`${this.apiUrl}/Instructor/${instructorId}`);
    }

    getClassroomById(id: number): Observable<ClassroomDTO> {
        return this.http.get<ClassroomDTO>(`${this.apiUrl}/${id}`);
    }

    deleteClassroom(id: number): Observable<string> {
        return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
    }
}

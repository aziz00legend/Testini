import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {
  private apiUrl = 'http://localhost:8080/api/challenges'; // Adjust if needed

  constructor(private http: HttpClient) {}

  createEmptyChallenge(instructorId: number): Observable<any> {
    const url = `${this.apiUrl}/empty?instructorId=${instructorId}`; // Assuming the API expects instructorId as a query param
    return this.http.post(url, {});
  }

  // Save the updated challenge data
  saveChanges(
    challengeId: number | null, // Include challengeId
    challengeName: string,
    challengeDesc: string,
    selectedTemplate: string,
    questions: any[],
    instructorId: number
  ): Observable<any> {
    const formattedDate = new Date();
    const challengeData = {
      title: challengeName,
      description: challengeDesc,
      challengeData: JSON.stringify({ questions }),
      templateName: selectedTemplate,
      status: 'IN_PROGRESS',
      createdAt: formattedDate,
    };
  
    const url = `${this.apiUrl}/save/${challengeId}?instructorId=${instructorId}`;
    return this.http.post(url, challengeData); // Send data to the backend
  }
  

  loadChallenge(challengeId: number,instructorId: number): Observable<any>{
    const url = `${this.apiUrl}/load/${challengeId}?instructorId=${instructorId}`; 
    return this.http.get(url, {});
  }

  publishChallenge(challengeId: number): Observable<any> {
    const url = `${this.apiUrl}/publish/${challengeId}`;
    return this.http.put(url, {});
  }

  // Function to get challenges for a specific instructor
  getChallengesByInstructor(instructorId: number): Observable<any> {
    const url = `${this.apiUrl}/display/${instructorId}`;
    return this.http.get(url,{});
  }
}

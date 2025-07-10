export interface ChallengeDisplayDTO {
    id: number;
    title: string;
    numQuestions: number;
    status: string;          // e.g., 'PUBLISHED', 'DRAFT', etc.
    creationDate: string;    // Format ISO string, ex: '2025-07-09T12:34:56'
    numberOfUses: number;
    templateName: string;
}

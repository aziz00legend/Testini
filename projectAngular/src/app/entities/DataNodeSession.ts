export interface DataNodeSession {
    id: number; // Session ID
    title: string; // Session Title
    students: {
        [email: string]: {
            id: number; // Student essai
            score: number; // Student score
        };
    };
}

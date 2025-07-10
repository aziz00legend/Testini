export interface SessionSaveUpdateDTO {
    id: number;
    title: string;
    sessionCode: string;
    startTime: Date | string;
    endTime: Date | string;
    createdAt: Date | string;
}

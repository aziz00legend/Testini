import { Result } from "./Result";

export interface SessionDTO {
    id: number;
    title: string;
    results: Result[];
}
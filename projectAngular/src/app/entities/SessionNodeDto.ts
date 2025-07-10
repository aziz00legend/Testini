import { DataNodeSession } from "./DataNodeSession";

export interface SessionNodeDto {
    data: DataNodeSession; // Session data
    children: SessionNodeDto[]; // Child sessions for hierarchical structure
}

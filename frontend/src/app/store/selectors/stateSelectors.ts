import { createSelector } from "@ngrx/store";
import { AppState } from "src/app/store/models/edifyState";

export const selectExpanded = (state: AppState) => state;
export const expandedSelector = createSelector(
    selectExpanded,
    (state) => state.navbar.expanded
);
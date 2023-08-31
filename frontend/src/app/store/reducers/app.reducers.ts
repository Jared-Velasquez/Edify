import { AppState } from 'src/app/store/models/edifyState';
import { navbarReducer } from './navbar.reducers';

export const appReducer = {
    navbar: navbarReducer
}
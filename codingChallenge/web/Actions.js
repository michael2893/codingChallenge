import {
    TAKE_ACTION_CLICK,
    HANDLE_INPUT
} from './Actionconstants';

export function handleInput(input){
    return {
        type: HANDLE_INPUT,
        input
    };
}

export function handleClick(){
    return {
        type: TAKE_ACTION_CLICK
    };
}
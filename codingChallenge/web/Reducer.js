import React, { Component } from 'react';

import {
    TAKE_ACTION_CLICK,
    HANDLE_INPUT
} from './Actionconstants';

export default function(state = {
    clicked: false,
    input: ''
}, action) {
    switch (action.type) {
        case TAKE_ACTION_CLICK:
            return Object.assign({}, state, {
                clicked: true
            });
        case HANDLE_INPUT:
             return Object.assign({}, state , {
                input: action.input
        });

    }
}
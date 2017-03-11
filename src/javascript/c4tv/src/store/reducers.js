import { combineReducers } from 'redux';


function gameState(state={
  topView: Array(7).fill(''),
  frontView: Array(7).fill(Array(7).fill(''))
}, action){
  switch(action.type){
    default:
      return state;
  }
}

function gameSettings(state={
  numberOfRows: 7,
  numberOfColumns: 7,
  canvasWidth: 800,
  canvasHeight: 500
}, action){
  switch(action.type){
    default:
      return state;
  }
}

export default combineReducers({
  gameState,
  gameSettings
});


export const setHoverStyle = (index) => {
  return {
    type: 'SET_HOVER_STYLE',
    index
  }
}

export const resetHoverStyle = () => {
  return {
    type: 'RESET_HOVER_STYLE'
  }
}


/*export const chooseColumn = (index) => {
  return {
    type: 'CHOOSE_COLUMN'
  }
} */


export const createNewGame = () => {
  //console.log('create new game action');
  return function (dispatch) {
    //console.log('create new game action nside');
    dispatch(chooseColumnStart);

    return fetch('http://192.168.2.97:8080/api/game/new')
            .then((json) => {
              dispatch(chooseColumnSuccess(json))
            })
            .catch((error) => {
              dispatch(chooseColumnError(error))
            })

  }
}

export const chooseColumn = (index, playerID) => {
  return function (dispatch) {

    dispatch(chooseColumnStart);

    let headers = new Headers();

    let data = JSON.stringify({player: playerID, y: index});
    //data.append('json', JSON.stringify({a: "Test"}));

    //console.log(data);

    let postObject = {
      method: 'POST',
      headers: new Headers({
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Content-Length': data.length
        }),
      mode: 'cors',
      body: data

    }

    return fetch('http://192.168.2.97:8080/api/game/field/blocks', postObject)
              .then((response) => {
                console.log('response from server', response);
                return response.json();
              })
              .then((json) => {
                dispatch(chooseColumnSuccess(json))
              })
              .catch((error) => {
                dispatch(chooseColumnError(error))
              })

  }
}

const chooseColumnStart = () => {
  return {
    type: 'CHOOSE_COLUMN_START'
  }
}

const chooseColumnSuccess = (fieldObject) => {
  console.log('column success action, front view: ', fieldObject.fullView);
  return {
    type: 'CHOOSE_COLUMN_SUCCESS',
    topView: fieldObject.topView1d,
    frontView: fieldObject.frontView
  }
}

const chooseColumnError = (error) => {
  console.log('column fail action', error);
  return {
    type: 'CHOOSE_COLUMN_FAILURE'
  }
}

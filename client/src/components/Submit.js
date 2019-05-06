import React from 'react';

const Submit = (props) => {
    return (
        <div className="form-element">
            <input
                id="button"
                type="button"
                value={props.value}
                onClick={props.onClick}
            />
        </div>

    )
}

export default Submit;
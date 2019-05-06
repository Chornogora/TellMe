import React from 'react';

const Image = (props) => {
    return (
        <div className="form-image">
            <label htmlFor={props.name} className="form-label">{props.title}</label>
            <input
                className="form-image"
                id={props.name}
                name={props.name}
                type={props.type}
                value={props.value}
                onChange={props.handleChange}
                placeholder={props.placeholder}
            />
        </div>
    )
}

export default Image;
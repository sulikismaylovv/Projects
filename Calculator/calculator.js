var value = '0';
var first_number;
var operator_1;
var second_number;
var operator_2;

const button = document.querySelectorAll('button');
const display = document.getElementById("display");

function updateDisplay(){
    display.innerHTML = value;
}

function clickButton(){
    button.forEach(function (btn_pressed) {
        btn_pressed.addEventListener('click', () => {
            if(btn_pressed.classList.contains("operand")){
                addOperand(btn_pressed.value);
                updateDisplay();
            }
            if(btn_pressed.classList.contains("operator")){
                if(btn_pressed.value == "delete_all"){
                    delete_all();
                    console.log(btn_pressed.value);
                }
                if(btn_pressed.value == "delete"){
                    delete_last();
                    console.log(btn_pressed.value);
                }
                if(btn_pressed.value == "neg"){
                    negate();
                    updateDisplay();
                    console.log(btn_pressed.value);
                }
                if(btn_pressed.value == "decimal"){
                    decimal();
                    console.log(btn_pressed.value);
                }
                if(btn_pressed.value == "equals"){
                    equals();
                    console.log(btn_pressed.value);
                }
                if(btn_pressed.value == "+" || btn_pressed.value == "-" || btn_pressed.value == "*" || btn_pressed.value == "÷"){
                    addOperator(btn_pressed.value)
                }
            }
            
        });
    });
}

function addOperand(operand){
    if (first_number == null){
        if (value == '0' || value == 0){
            //1st click - handles first operand input
            value = operand;}
        else if(value == first_number){
            //starts new operation after equals()
            value += operand
        }
        else{
            value += operand;
        }
    }
    else{
        //3rd/5th click - inputs to second number
        if(value == first_number){
            value = operand;
        }
        else{
        value += operand;}
    }
}

function addOperator(operator){
    if (first_number != null && second_number != null){
        second_number = value;
        value = calculate(Number(first_number), Number(second_number), operator_2);
        operator_2 = operator;
        first_number = value;
    }
    if (first_number != null && second_number == null){
        second_number = value;
        operator_2 = operator;
        value = calculate(Number(first_number), Number(second_number), operator_1);
        first_number = value;
    }
    else{
    first_number = value;
    operator_1 = operator; }
    updateDisplay()

}

function equals(){
    if (first_number == null){
        value = first_number;
    }
    else if (second_number!=null){
        value = calculate(Number(first_number), Number(second_number), operator_1);
    }
    else{
        second_number = value;
        value = calculate(Number(first_number), Number(second_number), operator_1);
    }
    updateDisplay();
    first_number = value;
    second_number = null;
    operator_1 = null;
    operator_2 = null;
    value = null;
}

function delete_all(){
    first_number = null;
    second_number = null;
    operator_1 = null;
    operator_2=null;
    value = '0';
    updateDisplay();
}

function delete_last(){
    if(value.toString().length > 1){
    value = value.toString().substring(0, value.toString().length - 1);
    }
    else{
        value = '0';
    }
    updateDisplay();
}

function negate(){
    value = ( value * -1).toString();
}

function decimal(){
    var dot = ".";
    if(first_number == value || second_number == value){
        value = '0';
        value += dot;
    }
    if(!value.toString().includes(dot)){
        value += dot;
    }
    updateDisplay();
}

function calculate(x,y,op){
    let equation = 0;
    if(op === '+') {
        equation =  (x + y);
    } else if(op === '-') {
        equation = x - y;
    } else if(op === '*') {
        equation =  x * y;
    } else if(op === '÷') {
        if(y === 0) {
            return 'can not divide by 0';
        } else {
            equation =  x / y;
        }
    }
    console.log(equation);
    equation = roundAccurately(equation, 15);
    return equation

}

function roundAccurately(num, places) {
    return parseFloat(Math.round(num + 'e' + places) + 'e-' + places);
}

updateDisplay();
clickButton();
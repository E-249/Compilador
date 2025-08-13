*Esta primera fase solo incluye el Ensamblador*


# Ensamblador COS

## Hay dos tipos de instrucciones: las operaciones y los saltos.

Las operaciones tienen la forma `A % B`; donde `%` es la operación; `A` es un registro (\*1) o una indirección (\*2); y `B` es un registro (\*1), indirección (\*2) o valor (\*3). Se guardará en `A` el resultado de la operación. 
Hay dos excepciones:
* `A : B`, "asignación", donde el valor de `B` se guarda en `A`.
* `A ? B`, "comparación", donde la diferencia entre `A - B` se guarda en un registro especial llamado `cmp`.
Las operaciones disponibles actualmente son:
* \[`:`\] asignación
* \[`+`\] suma
* \[`-`\] resta
* \[`*`\] multiplicación
* \[`/`\] división
* \[`?`\] comparación
Existen operaciones especiales, también llamadas funciones. Tienen la forma `A $Nombre$ B`. En ocasiones, aunque se requiera usar un registro, este no se cambiará. Esto es debido a que, para mantener el formato, hará falta tanto un registro `A` como uno `B`.
Las operaciones especiales disponibles actualmente son:
* `$say$`: imprime el carácter del valor en O. Mantiene E intacto.
* `$listen$`: lee por consola un número, guardándolo en E. Mantiene O intacto.

Los saltos tienen la forma `% C`, donde `%` es la comparación y `C` es un registro (\*1), indirección (\*2) o valor (\*3). En caso de que se cumpla la comparación (`cmp % C`), realizará el salto.
Hay una excepción:
* `! C`, "siempre", donde siempre salta a `C`.
Las comparaciones para saltos disponibles actualmente son:
* \[`>`\] mayor
* \[`<`\] menor
* \[`=`\] igual
* \[`!`\] siempre
* \[`<=`\] o \[`=<`\] no mayor
* \[`=>`\] o \[`>=`\] no menor
* \[`<>`\] o \[`><`\] no igual

(\*1) Registro: funcionan como variables.
(\*2) Indirección: accedes a la dirección en la pila.
(\*3) Valor: valor numérico.

## Hay dos tipos de comentario: de una línea y multilínea.

Los comentarios de una línea tienen la forma de `Instr % Comentario`, donde `%` son los símbolos a partir de los cuales comenzará el comentario y terminará a fin de línea. Los comentarios serán ignorados al crear el *pseudoejecutable* `.ccos`.
Los símbolos para comentarios de una línea disponibles actualmente son:
* \[`?~`\]: Información
* \[`!~`\]: Aviso

Los comentarios multilínea tienen la forma de `% Comentario %`, donde `%` son los símbolos a partir de los cuales comenzará el comentario en su primera aparación y terminará en su segunda. Los comentarios serán ignorados al crear el *pseudoejecutable* `.ccos`.
El símbolo para comentarios multilínea disponible actualmente es:
* \[`~`\]: Comentario

## Hay dos tipos de sustituciones: las etiquetas, las expansiones y sus llamadas.

Las etiquetas tienen la forma de `%Nombre`, donde `%` son los símbolos a partir de los cuales comenzará el nombre (\*4) de la etiqueta. Una etiqueta guarda a su nombre el ordinal de la siguiente instrucción.
El símbolo para etiquetas disponibles actualmente es:
* \[`@`\]: Etiqueta

Las expansiones tiene la forma de `Nombre % Contenido $`, donde `%` son los símbolos a partir de los cuales comenzará el contenido (\*5) y `$` los símbolos donde terminará dicho contenido. Una expansión guarda a su nombre el contenido asociado. Dentro de este contenido se pueden emplear los símbolos de sustituto, que serán reemplazados tras la llamada.
Los símbolos para expansiones disponibles actualmente son:
* \[`[`\]: Apertura (sustituye a `%`)
* \[`]`\]: Cierra (sustituye a `$`)
* \[`^`\]: Sustituto

Las llamadas tienen la forma `Nombre` para llamadas de etiquetas y expansiones sin sustituto, y `Nombre Reemplazo` para expansiones con sustituto, donde deberá haber espaciado entre nombre y reemplazo. Una llamada sustituye su aparición por el contenido asociado a su nombre. En caso de haber un reemplazo, se sustituirá el sustituto con el reemplazo. 

(\*4) Nombre: nombre compuesto por letras mayúsculas, minúsculas y/o barrabajas, de, al menos, dos caracteres.
(\*5) Contenido: cualquier tipo de texto que no contenga los símbolos de apertura o cierre de expansiones. No se pueden incluir llamadas dentro de expansiones.

## Hay un tipo de indirecciones: las de pila
Las indirecciones tienen la forma de `%D` donde `%` son los símbolos de indirección y `D` un registro. Un registro puede tener guardado una dirección en la pila. Acceder a dicha dirección mediante un registro se le llama indirección.
El símbolo para indirecciones disponible actualmente es:
* \[`#`\]: Indirección

## Finalmente, los registros
Hay 9 registros disponibles, cuyos usos por convenio son:
* \[`A`\]: registro de resultado
* \[`E`\]: primer argumento
* \[`O`\]: segundo argumento
* \[`I`\]: primer variable temporal
* \[`U`\]: segunda variable temporal
* \[`S`\]: stack, puntero de la parte superior de la pila. Referente principal de la pila
* \[`B`\]: base, puntero de la parte inferior de la pila. Separa regiones
* \[`P`\]: puntero, puntero auxiliar. Ayuda a realizar indirecciones a la pila
* \[`R`\]: retorno, dos usos: guarda el valor de retorno o guarda el código de error (puede que este sea retirado en un futuro)
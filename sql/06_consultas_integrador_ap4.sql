-- AP4: consultas de verificación, de SOLO LECTURA.
-- Se ejecutan después de crear tickets y órdenes desde Java.
USE sigetec;

-- 1. Tickets por prioridad. El menú 13 hace un resumen equivalente con Java.
SELECT prioridad, COUNT(*) AS cantidad FROM tickets
GROUP BY prioridad ORDER BY FIELD(prioridad, 'ALTA', 'MEDIA', 'BAJA');

-- 2. Detalle de orden con cliente, tipo, técnicos e intervención.
-- Se usan los nombres de columnas reales del esquema del TP3.
SELECT o.id_orden, c.nombre AS cliente, ti.id_ticket,
       ts.nombre AS tipo_servicio, te.nombre AS tecnico,
       i.descripcion AS trabajo_realizado, i.resultado, o.estado
FROM ordenes_trabajo o
JOIN tickets ti ON ti.id_ticket = o.id_ticket
JOIN clientes c ON c.id_cliente = ti.id_cliente
JOIN tipos_servicio ts ON ts.codigo = ti.tipo_codigo
LEFT JOIN asignaciones a ON a.id_orden = o.id_orden
LEFT JOIN tecnicos te ON te.id_tecnico = a.id_tecnico
LEFT JOIN intervenciones i ON i.id_orden = o.id_orden
ORDER BY o.id_orden, i.fecha_registro;

#ifdef DASH
#define DASH_PARAMS , v_line_distance
#else
#define DASH_PARAMS
#endif

$input v_color DASH_PARAMS

#include <common.sh>
#include <convert.sh>

void main()
{
    int material_index = int(u_state_material);
    SolidMaterial solid = read_solid_material(material_index);
    LineMaterial mat = read_line_material(material_index);
    
#include "fs_alpha.sh"
#include "fs_alphatest.sh"

#ifdef DASH
    if (mod(v_line_distance, mat.dash_size + mat.dash_gap) > mat.dash_size) discard; // todo - FIX
    v_color = vec4(hsl_to_rgb(vec3(v_line_distance, 1.0, 0.5)), 1.0);
#endif

    vec4 color = vec4(solid.color.rgb, solid.color.a * alpha);
#ifdef VERTEX_COLOR
    color *= v_color;
#endif

    gl_FragColor = vec4(color.rgb, color.a);
}
